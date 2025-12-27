function toggleChatbot() {
    const chatbotWindow = document.getElementById('chatbot-window');
    if (chatbotWindow.style.display === 'none' || chatbotWindow.style.display === '') {
        chatbotWindow.style.display = 'flex';
        document.getElementById('chatbot-input-field').focus();
    } else {
        chatbotWindow.style.display = 'none';
    }
}

async function askChatbot() {
    const inputField = document.getElementById('chatbot-input-field');
    const question = inputField.value.trim();
    if (!question) return;

    addMessage(question, 'user-message');
    inputField.value = '';

    try {
        const response = await fetch('/api/chatbot/ask', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ question: question })
        });
        const data = await response.json();
        addMessage(data.answer, 'bot-message');
    } catch (error) {
        addMessage("Error connecting to server.", 'bot-message');
    }
}

function addMessage(text, className) {
    const messagesDiv = document.getElementById('chatbot-messages');
    const messageDiv = document.createElement('div');
    messageDiv.classList.add('message', className);
    messageDiv.innerText = text;
    messagesDiv.appendChild(messageDiv);
    messagesDiv.scrollTop = messagesDiv.scrollHeight;
}

// Allow Enter key to send message
document.addEventListener('DOMContentLoaded', () => {
    document.getElementById('chatbot-input-field').addEventListener('keypress', function (e) {
        if (e.key === 'Enter') {
            askChatbot();
        }
    });
});
