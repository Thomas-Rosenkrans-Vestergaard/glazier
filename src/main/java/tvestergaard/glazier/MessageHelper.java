package tvestergaard.glazier;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Queue;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Helper for sending messages to the users of the site.
 *
 * @author Thomas
 */
public class MessageHelper implements Iterator<Object> {

    /**
     * The user {@link HttpSession} of the user making a request.
     */
    private final HttpSession session;

    /**
     * The {@link Queue} of messages to send to them.
     */
    private final Queue<Object> messages;

    /**
     * Creates a new {@link MessageHelper}.
     *
     * @param request The {@link HttpServletRequest}.
     */
    public MessageHelper(HttpServletRequest request) {
        this.session = request.getSession();
        this.messages = ensureStack();
        request.setAttribute("messages", this);
    }

    /**
     * Adds a new message to the queue.
     *
     * @param message The message to add.
     */
    public void addMessage(Object message) {
        messages.add(message);
    }

    @Override
    public boolean hasNext() {
        return !messages.isEmpty();
    }

    @Override
    public Object next() {
        return messages.poll();
    }

    /**
     * Retrieves the queue from the session, or creates a new one.
     *
     * @return The queue.
     */
    private Queue<Object> ensureStack() {

        Object attribute = session.getAttribute("messages");

        if (attribute == null || !(attribute instanceof ArrayDeque)) {
            Queue<Object> messages = new ArrayDeque<>();
            session.setAttribute("messages", messages);
            return messages;
        } else {
            Queue<Object> messages = (ArrayDeque) attribute;
            session.setAttribute("messages", messages);
            return messages;
        }
    }
}
