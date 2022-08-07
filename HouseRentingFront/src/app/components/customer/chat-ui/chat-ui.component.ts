import { Component, OnDestroy, OnInit } from '@angular/core';
import * as SockJS from 'sockjs-client';
import { HeaderServiceService } from 'src/app/services/header-service.service';
import { MessageService } from 'src/app/services/message.service';
import { SessionService } from 'src/app/services/session.service';
import * as Stomp from '@stomp/stompjs';
@Component({
  selector: 'app-chat-ui',
  templateUrl: './chat-ui.component.html',
  styleUrls: ['./chat-ui.component.css'],
})
export class ChatUiComponent implements OnInit, OnDestroy {
  chats: any;
  selectedChat: any;
  user: any;
  id: number = 0;
  chatId: number = 0;
  messages: any;
  stompClient: any;
  form: any = {
    message: '',
  };

  sForm: any = {
    word: '',
  };
  constructor(
    private messageService: MessageService,
    private sessionService: SessionService,
    private headerService: HeaderServiceService
  ) {}

  ngOnDestroy(): void {
    if (this.stompClient != null) {
      this.stompClient.disconnect();
    }
  }
  ngOnInit(): void {
    const user = this.sessionService.getUser();
    if (user) {
      const userData = JSON.parse(user);
      this.user = userData;
      this.id = this.user.id;
      this.getChatInfo(this.user.id);
      this.headerService.updateCustomerHeader();
      this.initializeChat();
    }
  }

  onSendSubmit() {
    const { message } = this.form;
    const data = {
      chatChannelId: this.selectedChat.id,
      userId: this.user.id,
      username: this.user.username,
      dateCreated: this.selectedChat.dateCreated,
      content: message,
      datePosted: new Date(),
    };
    this.messageService.sendMessage(data).subscribe((res) => {
      this.selectedChat.messages.push(res.data);
    });
  }

  onSearchSubmit() {
    const { word } = this.sForm;
    if (word !== '') {
      this.chats = this.chats.filter((item: { channelName: string }) =>
        item.channelName
          .split(' ')
          .join('')
          .toLowerCase()
          .includes(word.split(' ').join('').toLowerCase())
      );
    } else {
      this.getChatInfo(this.user.id);
    }
  }

  selectProperty(id: number) {
    this.messageService.findChannelById(id).subscribe((res) => {
      this.selectedChat = res.data;
    });
  }

  getChatInfo(id: number): void {
    this.messageService.getUserChatInfo(id).subscribe((res) => {
      this.chats = res.data;
      this.selectedChat = res.data[0];
      this.chatId = this.selectedChat.id;
      this.messageService.findChannelById(res.data[0].id).subscribe((res) => {
        this.selectedChat = res.data;
        this.chatId = this.selectedChat.id;
      });
    });
  }

  initializeChat(): void {
    const socket = new SockJS('http://localhost:8082/chat-websocket');
    this.stompClient = Stomp.over(socket);
    this.stompClient.connect({}, () => {
      this.stompClient.subscribe('/chat/channel', (res: any) => {
        if (res)
          this.selectedChat.messages.push(JSON.parse(res.body).body.data);
      });
    });
  }

  sendMessage(): void {
    const { message } = this.form;
    if (message && message != '' && this.stompClient != null) {
      const data = {
        chatChannelId: this.selectedChat.id,
        userId: this.user.id,
        username: this.user.username,
        dateCreated: this.selectedChat.dateCreated,
        content: message,
        datePosted: new Date(),
      };
      this.stompClient.send('/app/message', {}, JSON.stringify(data));
    } else {
      alert('You need to enter message');
    }
  }
}
