package de.erna.krautmail.collectors.imap.adapters

import de.erna.krautmail.collectors.imap.{ImapConnectionInfo, ImapAdapter}
import scala.concurrent.{ExecutionContext, Future}
import javax.mail.Session
import java.util.Properties
import grizzled.slf4j.Logging

/**
 * User: Eros Candelaresi <eros@candelaresi.de>
 * Project: server
 * Date: 18.10.2013
 * Time: 09:39
 */
class JavaMailImapAdapter(implicit val ec: ExecutionContext) extends ImapAdapter[JavaMailImapConnectionContext] with Logging {

  /**
   * Creates a fresh <code>ImapConnectionContext</code> and initializes it with empty/default values.

   * @return A new, empty <code>ImapConnectionContext</code> suitable for keeping the adapters internal data.
   */
  def createContext() = new JavaMailImapConnectionContext()

  /**
   * Connect to the given IMAP server using the provided connection info.
   *
   * @param connectionInfo Connection information like host, username, password, etc.
   * @param context A context object that might have been filled before, eg. from previous failed connection attempts,
   *                etc. In most cases it will be empty
   * @return A future that will contain a <b>copy</b> of the context object with updated values.
   */
  def connect(connectionInfo: ImapConnectionInfo, context: JavaMailImapConnectionContext): Future[JavaMailImapConnectionContext] = {
    Future {
      trace("Setting up IMAP session and store...")
      val session = Session.getDefaultInstance(new Properties())
      val store = session.getStore("imap")
      trace("Done setting up IMAP session and store.")

      debug("Connecting to IMAP server...")
      store.connect(connectionInfo.endpoint.getHostName,
        connectionInfo.endpoint.getPort,
        connectionInfo.username,
        connectionInfo.password)

      debug("Done connecting to IMAP server.")

      context.copy(session = Some(session), store = Some(store))
    }
  }
}
