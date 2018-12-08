package org.ilya40umov.kc.classic.combine.demo

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import kotlin.random.Random

@RestController
@RequestMapping("/demo/random_user_id")
class RandomUserIdPicker {

    @GetMapping
    fun randomUserId(): String = Random.nextInt(1, Int.MAX_VALUE).toString()
}