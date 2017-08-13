package me.acomagu.slock.adapters.presenters

import me.acomagu.slock.contracts.{Callback, OutputPort}

import scala.concurrent.{ExecutionContext, Future, Promise}

object UnitPresenter extends OutputPort[Callback[Unit]] {

  def callback(promise: Promise[Unit]) = new Callback[Unit] {
    def onSuccess(result: Unit): Unit = promise.success(result)
    def onFailure(t: Throwable): Unit = promise.failure(t)
  }

  override type Rendered = Unit

  override def execute(call: Callback[Unit] => Unit)(implicit ec: ExecutionContext): Future[Unit] = {
    val promise = Promise[Unit]()

    call(callback(promise))

    promise.future
  }
}