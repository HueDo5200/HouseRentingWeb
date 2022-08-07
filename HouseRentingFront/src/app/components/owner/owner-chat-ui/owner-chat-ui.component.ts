import { Component, OnDestroy, OnInit } from '@angular/core';
import { HeaderServiceService } from 'src/app/services/header-service.service';
import { MessageService } from 'src/app/services/message.service';
import { SessionService } from 'src/app/services/session.service';
import * as SockJS from 'sockjs-client';
import * as Stomp from '@stomp/stompjs';
@Component({
  selector: 'app-owner-chat-ui',
  templateUrl: './owner-chat-ui.component.html',
  styleUrls: ['./owner-chat-ui.component.css'],
})
export class OwnerChatUiComponent implements OnInit, OnDestroy {
  chats: any;
  selectedChat: any;
  ownerId: number = 0;
  user: any;
  chatId: number = 0;
  messages: any;
  stompClient:any;
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
    if(this.stompClient != null) {
      this.stompClient.disconnect();
    }
   }
  ngOnInit(): void {
    const user = this.sessionService.getUser();
    if (user) {
      const userData = JSON.parse(user);
      this.user = userData;
      this.ownerId = userData.id;
      this.getChatInfo(this.ownerId);
      this.headerService.updateOwnerHeader();
      this.initializeChat();
    }

    // setInterval(() => {
    //   if (this.chatId != 0) { 
    //     this.messageService.findChannelById(this.chatId).subscribe((res) => {
    //       this.selectedChat = res.data;
    //     });
    //   }
    // }, 1000);
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
      console.log('selected ' + this.selectedChat.messages);
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
      this.getChatInfo(this.ownerId);
    }
  }

  selectProperty(id: number) {
    this.messageService.findChannelById(id).subscribe((res) => {
      
      this.selectedChat = res.data;
    });
  }

  getChatInfo(id: number): void {
    this.messageService.getOwnerChatInfo(id).subscribe((res) => {
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
    const socket = new SockJS('http://localhost:8082/chat-websocket')
    this.stompClient = Stomp.over(socket);
    this.stompClient.connect({}, () => {
        this.stompClient.subscribe('/chat/channel', (res: any) => {
            if(res) {
              console.log('response ' + JSON.stringify(JSON.parse(res.body).body.data))
              this.selectedChat.messages.push(JSON.parse(res.body).body.data);
            }
           
        })
    })
  }

  sendMessage(): void {
    const { message } = this.form;
    if(message && message != "") {
      const data = {
        chatChannelId: this.selectedChat.id,
        userId: this.user.id,
        username: this.user.username,
        dateCreated: this.selectedChat.dateCreated,
        content: message,
        datePosted: new Date(),
      };
      this.stompClient.send("/app/message", {},
      JSON.stringify(data)
      )
    } else {
      alert("You need to enter message")
    }
    
  }

}
