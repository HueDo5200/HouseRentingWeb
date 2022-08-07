import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { CustomerService } from 'src/app/services/customer.service';
import { SessionService } from 'src/app/services/session.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent  {
  loginFailed: boolean = false;
  user:any;
  form: any = {
    username: '',
    password: '',
  };
  constructor(
    private router: Router,
    private authService: AuthService,
    private customerService: CustomerService,
    private sessionService: SessionService
  ) {}

  onClickSubmit() {
    const { username, password } = this.form;
 
    this.authService.login(username, password).subscribe((res) => {
      if (!res) {
        this.loginFailed = true;
        alert("Login failed! Username or password is not correct!")
      } else {
        const data = res.data;
        const token = data.jwtToken;
        this.updateHeader();
        this.saveUserToSessionStorage(
          { username: data.username, email: data.email, id: data.id, role: data.role, phone: data.phone, firstName: data.firstName, lastName: data.lastName },
          token
        );
        this.naviageTo(data.role, data.id);
      }
    });
  }

  updateHeader() {
    this.customerService.show.next(false);
  }
 

  saveUserToSessionStorage(user: any, token: string) {
    this.sessionService.saveUser(user);
    this.sessionService.saveToken(token);
  }

  naviageTo(role: any, userId: number) {
    if (role.name.toLowerCase() == 'Customer'.toLowerCase()) {
      this.customerService.loggedIn.next(true);
      this.router.navigate(['/property-list']);
    } else {
      this.customerService.isAdmin.next(true);
      this.router.navigate(['/manage-property']);
    }
  }

}
