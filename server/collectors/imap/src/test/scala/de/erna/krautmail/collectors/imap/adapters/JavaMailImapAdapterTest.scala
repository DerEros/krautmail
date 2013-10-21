package de.erna.krautmail.collectors.imap.adapters

import org.specs2.mutable
import com.icegreen.greenmail.util.{ServerSetupTest, GreenMail}
import de.erna.krautmail.collectors.imap.ImapConnectionInfo
import java.net.InetSocketAddress
import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import org.specs2.time.NoTimeConversions
import scala.concurrent.duration._
import scala.util.Failure
import javax.mail.AuthenticationFailedException
import java.util.concurrent.TimeoutException

/**
 * User: Eros Candelaresi <eros@candelaresi.de>
 * Project: server
 * Date: 20.10.2013
 * Time: 19:33
 */
class JavaMailImapAdapterTest extends mutable.Specification with NoTimeConversions {
  /**
   * Used to ensure that every instance of the Greenmail test server has different ports. Otherwise the concurrent
   * execution of unit tests will have unexpected results.
   */
  var portOffset = 3000

  trait mailServer extends mutable.BeforeAfter {
    var (server, imapPort) = synchronized {
      portOffset += 1
      ServerSetupTest.setPortOffset(portOffset)
      (new GreenMail(ServerSetupTest.IMAP), ServerSetupTest.IMAP.getPort)
    }

    val endpoint = new InetSocketAddress("localhost", imapPort)
    val adapter = new JavaMailImapAdapter
    val defaultConnectionInfo = ImapConnectionInfo(endpoint, "someUsername", "somePassword")
    val defaultConnectionContext = adapter.createContext()

    def before: Any = {
      server.start()
      server.setUser("user@example.com", "someUsername", "somePassword")

    }

    def after: Any = {
      server.stop()
    }

  }

  "On connect the JavaMail IMAP adapter" should {
    "connect successfully when provided with correct data" in new mailServer {
      val futureResult = adapter.connect(defaultConnectionInfo, defaultConnectionContext)
      val result = Await.result(futureResult, 1 seconds)

      result.session should not beNone

      result.store should not beNone

      result.store.get.isConnected should beTrue

    }

    "throw AuthenticationFailedException with incorrect credentials" in new mailServer {
      val connectionInfo = ImapConnectionInfo(endpoint, "someUsername", "wrongPassword")

      val futureResult = adapter.connect(connectionInfo, defaultConnectionContext)
      Await.ready(futureResult, 1 seconds)

      futureResult.value should haveClass[Some[Failure[AuthenticationFailedException]]]
    }

    "throw IllegalStateException when trying to connect twice" in new mailServer {
      /* 1st connect */
      adapter.connect(defaultConnectionInfo, defaultConnectionContext)

      /* 2nd connect */
      val futureResult = adapter.connect(defaultConnectionInfo, defaultConnectionContext)
      Await.ready(futureResult, 1 seconds)

      futureResult.value should haveClass[Some[Failure[IllegalStateException]]]
    }

    "time out after a specified amount of time if service does not respond" in {
      val adapter = new JavaMailImapAdapter
      val endpoint = new InetSocketAddress("localhost", 1234)
      val connectionInfo = ImapConnectionInfo(endpoint, "someUsername", "somePassword")
      val connectionContext = adapter.createContext()

      val futureResult = adapter.connect(connectionInfo, connectionContext)

      Await.result(futureResult, 10 second) must throwA[TimeoutException] //TODO: this should be an exception thrown by the adapter, not by the test
    }

  }

}
