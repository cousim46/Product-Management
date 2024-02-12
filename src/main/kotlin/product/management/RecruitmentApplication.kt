package product.management

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class RecruitmentApplication

fun main(args: Array<String>) {
	runApplication<RecruitmentApplication>(*args)
}
