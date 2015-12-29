package net.itsprime;

import in.kyle.ezskypeezlife.api.obj.SkypeMessage;
import in.kyle.ezskypeezlife.events.conversation.SkypeMessageReceivedEvent;
import org.apache.commons.lang.StringUtils;

/**
 * Created by Jham on 12/29/2015.
 */
public class SkypeCMDs {
    public void onMessage(SkypeMessageReceivedEvent event) {
        String message = event.getMessage().getMessage(); // Get the message content
        System.out.println("Got message: " + event.getMessage().getSender().getUsername() + " - " + message);
        String[] args = message.split(" ");

    switch (args[0].toLowerCase()) {
        case "+ping":  // Replies to a conversation with the message pong
            event.reply("Pong!");
            break;
        case "+me":  // Prints out user information
            event.reply("You: " + event.getMessage().getSender());
            break;
        case "+edit":  // Tests message editing
            SkypeMessage sendMessage = event.reply("Testing message " + "editing...");
            sendMessage.edit("Message editing works!");
            event.reply("Message edit test complete");
            break;
        case "+topic": // Sets the conversation topic
            if (args.length > 0) {
                String topic = StringUtils.join(args, ' ', 1, args.length);
                event.getMessage().getConversation().changeTopic(topic);
                event.reply("Topic set to '" + topic + "'");
            } else {
                event.reply("Usage: +topic topic");
            }
            break;
        case "+contact": // Set user as a contact
            if (args.length > 0) {
                boolean contact = Boolean.parseBoolean(args[1]);
                event.getMessage().getSender().setContact(contact);
                event.reply("Set contact: " + contact);
            } else {
                event.reply("Usage: +contact boolean");
            }
            break;
        case "+info": // Get current conversation information
            event.reply(event.getMessage().getConversation().toString());
            // Specific details about a conversation (The topic, who made it, all the users in it) may not have already been loaded
            // Calling fullyLoad will load in the important data we need if it is not already loaded
            event.getMessage().getConversation().fullyLoad();
            event.reply(event.getMessage().getConversation().toString());
            break;
        }
    }
}
