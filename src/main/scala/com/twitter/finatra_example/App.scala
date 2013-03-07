package com.twitter.finatra_example

import com.twitter.finatra._

import com.twitter.finagle.redis.Client
import com.twitter.finagle.redis.util.{StringToChannelBuffer, CBToString}

class ExampleApp extends Controller {

  val redisClient = Client("localhost:6379")

  get("/:id") { request =>
    redisClient.get( StringToChannelBuffer(request.routeParams("id")) ) map {

      case Some(value) => render.plain("Found: " + CBToString(value))
      case None => render.plain("No value for that key found in Redis")
    }
  }

}

object App {

  def main(args: Array[String]) = {
    FinatraServer.register(new ExampleApp)
    FinatraServer.start()
  }

}
