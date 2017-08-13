package me.acomagu.slock.contracts

import scala.concurrent.{ExecutionContext, Future}

trait OutputPort[C <: Callback[_]] {

  type UseCaseExecutor = C => Unit

  type Rendered

  def execute(call: UseCaseExecutor)(implicit ec: ExecutionContext): Future[Rendered]
}