import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'chatDate',
})
export class ChatDatePipe implements PipeTransform {
  transform(value: Date, ...args: unknown[]): any {
    if (value) {
      const diff = new Date().getFullYear() - new Date(value).getFullYear();
      if(diff > 1) {
       return diff + ' year ago';
      }
      if(diff == 1) {
        return ' one year ago';
      }
      console.log('diff ' + diff);
      if(diff < 1) {
         const diff2 = new Date().getDate() - new Date(value).getDate();
         if(diff2 <= 1) {
          return ' today';
         } 
         if(diff2 < 7) {
          return  Math.floor(diff2) + ' days ago';
        }
        if(diff2 == 7) {
          return  Math.floor(diff2 / 7) + ' week ago'
        }
        if(diff2 < 30) {
          return Math.floor(diff2 / 7) + ' weeks ago'
        }
       
         if(diff2 == 30) {
           return  Math.floor(diff2 / 30) + ' month ago'
         }
      
         if(diff2 > 30) {
          return Math.fround(diff2 / 12) + ' months ago'
        }
        
        
         
      }
      
    }
  }
}
