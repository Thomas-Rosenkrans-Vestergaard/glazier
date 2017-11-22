package tvestergaard.glazier;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Queue;
import java.util.Stack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class MessageHelper implements Iterator<Object> {
    
    private final HttpSession session;
    private final Queue<Object> messages;
    
    public MessageHelper(HttpServletRequest request) {
        this.session = request.getSession();
        this.messages = ensureStack();
        request.setAttribute("messages", this);
    }
    
    public void addMessage(Object message) {
        messages.add(message);
    }
    
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
    
    @Override
    public boolean hasNext() {
        return !messages.isEmpty();
    }
    
    @Override
    public Object next() {
        return messages.poll();
    }
}
