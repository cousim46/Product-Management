package product.management.api.manager

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import product.demo.app.common.CommonResponse
import product.management.api.manager.dto.request.Longin
import product.management.api.manager.dto.request.ManagerCreate
import product.management.api.manager.dto.request.RandomNumber
import product.management.app.manager.ManagerWriteService
import product.management.token.LoginToken
import java.util.*
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse

@RequestMapping("/api/president")
@RestController
class ManagerController(
    private val managerWriteService: ManagerWriteService,
) {

    @PostMapping
    fun join(@RequestBody managerCreate: ManagerCreate): CommonResponse {
        managerWriteService.create(
            phone = managerCreate.phone,
            password = managerCreate.password,
            salt = RandomNumber.create()
        )
        return CommonResponse.toResponse()
    }
    @PostMapping("/login")
    fun login(@RequestBody login: Longin, httpServletResponse: HttpServletResponse) : CommonResponse {
        val loginToken: LoginToken = managerWriteService.login(login.phone,login.password, Date())
        val cookie = Cookie("loginInfo",loginToken.access)
        httpServletResponse.addCookie(cookie)
        return CommonResponse.toResponse()
    }
}