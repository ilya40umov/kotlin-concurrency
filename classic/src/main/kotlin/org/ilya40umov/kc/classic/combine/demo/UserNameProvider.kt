package org.ilya40umov.kc.classic.combine.demo

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/demo/user_name")
class UserNameProvider {

    @GetMapping
    fun randomUserId(
        @RequestParam id: Int
    ): String = if (id % 2 == 0) "Jack" else "Jill"

}