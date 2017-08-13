package me.acomagu.slock.contracts

trait Callback[Result] {
  def onSuccess(result: Result): Unit
  def onFailure(t: Throwable): Unit
}