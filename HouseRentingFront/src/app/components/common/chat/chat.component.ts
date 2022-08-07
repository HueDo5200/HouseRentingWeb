import { Component, OnDestroy, OnInit } from '@angular/core';
import { HeaderServiceService } from 'src/app/services/header-service.service';
import { MessageService } from 'src/app/services/message.service';
import { SessionService } from 'src/app/services/session.service';
import * as SockJS from 'sockjs-client';
import * as Stomp from '@stomp/stompjs';
@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnInit, OnDestroy {

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
      console.log(userData);
      this.ownerId = userData.id;
      this.getChatInfo(this.ownerId);
      if(this.user.role.name == 'owner') {
        this.headerService.updateOwnerHeader();
      } else {
        this.headerService.updateCustomerHeader();
      }
    
    }
    if(this.chatId != 0) {
      this.messageService.findChannelById(this.chatId).subscribe((res) => {
        this.selectedChat = res.data;
      });     
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
     this.chatId = id;
    });
  }

  getChatInfo(id: number): void {
    this.messageService.getOwnerChatInfo(id).subscribe((res) => {
      this.chats = res.data; 
      console.log('this chats ' + JSON.stringify(this.chats));
      this.selectedChat = res.data[0];
      this.chatId = this.selectedChat.id;
      this.messageService.findChannelById(res.data[0].id).subscribe((res) => {
        this.selectedChat = res.data;
        this.chatId = this.selectedChat.id;
      });
    });
  }

  initializeChat(): void {
    const socket = new SockJS('http://localhost:8081/chat-websocket')
    this.stompClient = Stomp.over(socket);
    this.stompClient.connect({}, () => {
        this.stompClient.subscribe('/api/v1/chat/user', (res: any) => {
            console.log('response ' + res);
            // this.messages.push(res);
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
      this.stompClient.send('/app/send', {},
      JSON.stringify(data)
      )
    } else {
      alert("You need to enter message")
    }
    
  }

}
