package me.ilya40umov.kc.classic.combine

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import kotlin.random.Random

@RestController
@RequestMapping("/demo")
class DemoApiController {

    @GetMapping("/random_user_id")
    fun randomUserId(): String =
        Random.nextInt(1, Int.MAX_VALUE).toString()

    @GetMapping("/user_name")
    fun randomUserId(@RequestParam id: Int): String =
        if (id % 2 == 0) "Jack" else "Jill"
}