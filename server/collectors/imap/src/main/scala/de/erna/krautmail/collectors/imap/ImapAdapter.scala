package de.erna.krautmail.collectors.imap

import scala.concurrent.Future

/**
 * User: Eros Candelaresi <eros@candelaresi.de>
 * Project: server
 * Date: 18.10.2013
 * Time: 09:39
 *
 * Basic interface for all adapters that offer IMAP connectivity functionality. Note that there is a on-to-one mapping
 * between the collector actor and the IMAP adapter. So there is a guarantee that the adapter will always only be
 * called from one thread at once. This avoids the need to synchronize. However, there is no guarantee that every call
 * will come from same thread!
 */
trait ImapAdapter[ContextType <: ImapConnectionContext] {

  /**
   * Creates a fresh <code>ImapConnectionContext</code> and initializes it with empty/default values.

   * @return A new, empty <code>ImapConnectionContext</code> suitable for keeping the adapters internal data.
   */
  def createContext(): ContextType

  /**
   * Connect to the given IMAP server using the provided connection info.
   *
   * @param connect Connection information like host, username, password, etc.
   * @param context A context object that might have been filled before, eg. from previous failed connection attempts,
   *                etc. In most cases it will be empty
   * @return A future that will contain a <b>copy</b> of the context object with updated values.
   */
  def connect(connect: ImapConnectionInfo, context: ContextType): Future[ContextType]
}
