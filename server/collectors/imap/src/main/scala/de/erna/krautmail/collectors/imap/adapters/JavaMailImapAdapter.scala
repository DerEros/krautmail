package de.erna.krautmail.collectors.imap.adapters

import de.erna.krautmail.collectors.imap.{ImapConnectionInfo, ImapAdapter}
import scala.concurrent.Future

/**
 * User: Eros Candelaresi <eros@candelaresi.de>
 * Project: server
 * Date: 18.10.2013
 * Time: 09:39
 */
class JavaMailImapAdapter extends ImapAdapter[JavaMailImapConnectionContext] {
  /**
   * Connect to the given IMAP server using the provided connection info.
   *
   * @param connect Connection information like host, username, password, etc.
   * @param context A context object that might have been filled before, eg. from previous failed connection attempts,
   *                etc. In most cases it will be empty
   * @return A future that will contain a <b>copy</b> of the context object with updated values.
   */
  def connect(connect: ImapConnectionInfo, context: JavaMailImapConnectionContext): Future[JavaMailImapConnectionContext] = {
    null
  }
}
