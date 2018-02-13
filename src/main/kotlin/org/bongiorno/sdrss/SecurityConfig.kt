package org.bongiorno.sdrss

import org.bongiorno.sdrss.domain.security.AclSid
import org.bongiorno.sdrss.domain.security.User
import org.bongiorno.sdrss.repositories.security.AclClassRepository
import org.bongiorno.sdrss.repositories.security.AclSidRepository
import org.bongiorno.sdrss.repositories.security.UserRepository
import org.reflections.Reflections
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.concurrent.ConcurrentMapCache
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.http.HttpMethod
import org.springframework.security.access.expression.SecurityExpressionHandler
import org.springframework.security.access.hierarchicalroles.RoleHierarchy
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl
import org.springframework.security.access.vote.RoleHierarchyVoter
import org.springframework.security.acls.AclPermissionEvaluator
import org.springframework.security.acls.domain.*
import org.springframework.security.acls.jdbc.BasicLookupStrategy
import org.springframework.security.acls.jdbc.JdbcAclService
import org.springframework.security.acls.jdbc.LookupStrategy
import org.springframework.security.acls.model.AclCache
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.authority.AuthorityUtils.createAuthorityList
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.FilterInvocation
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct
import javax.persistence.Entity
import javax.sql.DataSource

@EnableWebSecurity
@Configuration
@Order(1)
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfig {


    @Component
    class SecurityConfiguration(private val aclSidRepo: AclSidRepository,
                                private val classRepo: AclClassRepository,

                                private val userRepo: UserRepository) : WebSecurityConfigurerAdapter() {

        @PostConstruct
        private fun init() {
            val context = SecurityContextHolder.getContext()
            context.authentication = UsernamePasswordAuthenticationToken("system", "system",
                    createAuthorityList("ROLE_ADMIN"))


            val all = classRepo.findAll()
            val reflections = Reflections(this.javaClass.`package`.name)

            val entities = all - reflections.getTypesAnnotatedWith(Entity::class.java)
            entities.forEach { classRepo.save(it) }
            classRepo.saveAll(entities)


            SecurityContextHolder.clearContext()

        }

        @Autowired
        fun configureGlobalSecurity(uds: UserDetailsService, auth: AuthenticationManagerBuilder,
                                    encoder: PasswordEncoder, authProvider: AuthenticationProvider) {
            auth.userDetailsService(uds).passwordEncoder(encoder)
            auth.authenticationProvider(authProvider)

        }

        override fun configure(http: HttpSecurity) {
            http.httpBasic().and().authorizeRequests().//
                    antMatchers(HttpMethod.POST, "/**").hasRole("ADMIN").//
                    antMatchers(HttpMethod.PUT, "/**").hasRole("ADMIN").//
                    antMatchers(HttpMethod.GET, "/**").authenticated().//
                    antMatchers(HttpMethod.PATCH, "/**").hasRole("ADMIN").//
                    and().csrf().disable()

        }

    }

    @Bean
    internal fun pwdEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder(13)
    }

    @Bean
    fun authenticationProvider(userDetailsService: UserDetailsService, pwdEncoder: PasswordEncoder): AuthenticationProvider {
        val authenticationProvider = DaoAuthenticationProvider()
        authenticationProvider.setUserDetailsService(userDetailsService)
        authenticationProvider.setPasswordEncoder(pwdEncoder)
        return authenticationProvider
    }

    @Bean
    internal fun uds(users: UserRepository): UserDetailsService = UserDetailsService { user -> users.findById(user).orElse(null) }

    @Bean
    internal fun hierarchyVoter(roleHierarchy: RoleHierarchy): RoleHierarchyVoter {

        return RoleHierarchyVoter(roleHierarchy)
    }

    @Bean
    internal fun webExpressionHandler(roleHierarchy: RoleHierarchy): SecurityExpressionHandler<FilterInvocation> {
        val defaultWebSecurityExpressionHandler = DefaultWebSecurityExpressionHandler()
        defaultWebSecurityExpressionHandler.setRoleHierarchy(roleHierarchy)
        return defaultWebSecurityExpressionHandler
    }

    @Bean
    internal fun roleHierarchy(): RoleHierarchy {

        val roleHierarchy = RoleHierarchyImpl()
        roleHierarchy.setHierarchy("ROLE_ROOT > ROLE_ADMIN ROLE_ADMIN > ROLE_USER  ROLE_USER > ROLE_VISITOR")
        return roleHierarchy
    }

    //    http://krams915.blogspot.com/2011/01/spring-security-3-full-acl-tutorial_30.html
    /*
    public AclAuthorizationStrategyImpl(GrantedAuthority[] auths)
And here's what auths represent:
auths - an array of GrantedAuthoritys that have special permissions (index 0 is the authority needed to change ownership, index 1 is the authority needed to modify auditing details, index 2 is the authority needed to change other ACL and ACE details)

     */
    @Bean
    internal fun aclEvaluator(source: DataSource, lookupStrategy: LookupStrategy): AclPermissionEvaluator {
        return AclPermissionEvaluator(JdbcAclService(source, lookupStrategy))
    }

    @Bean
    internal fun lookupStrategy(source: DataSource, aclCache: AclCache, aclStrategy: AclAuthorizationStrategy, logger: AuditLogger): LookupStrategy {
        return BasicLookupStrategy(source, aclCache, aclStrategy, logger)
    }

    @Bean
    internal fun aclCache(aclStrategy: AclAuthorizationStrategy, auditLogger: AuditLogger): AclCache {
        return SpringCacheBasedAclCache(ConcurrentMapCache("aclcache"),
                DefaultPermissionGrantingStrategy(auditLogger), aclStrategy)
    }

    @Bean
    internal fun aclStrategy(): AclAuthorizationStrategy {
        val admin = SimpleGrantedAuthority("ROLE_ADMIN")
        return AclAuthorizationStrategyImpl(admin, admin, admin)
    }

    @Bean
    internal fun auditLogger(): AuditLogger {
        return ConsoleAuditLogger()
    }
}
