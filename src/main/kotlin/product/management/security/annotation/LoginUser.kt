package product.management.security.annotation

@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class LoginUser

