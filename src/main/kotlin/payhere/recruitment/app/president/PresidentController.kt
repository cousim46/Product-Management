package payhere.recruitment.app.president

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import payhere.demo.app.common.CommonResponse
import payhere.recruitment.app.president.dto.request.PresidentCreate
import payhere.recruitment.app.president.dto.request.RandomNumber

@RequestMapping("/api/president")
@RestController
class PresidentController(
    private val presidentWriteService: PresidentWriteService,
) {

    @PostMapping
    fun join(@RequestBody presidentCreate: PresidentCreate): CommonResponse {
        presidentWriteService.create(
            phone = presidentCreate.phone,
            password = presidentCreate.password,
            salt = RandomNumber.create()
        )
        return CommonResponse.toResponse()
    }
}