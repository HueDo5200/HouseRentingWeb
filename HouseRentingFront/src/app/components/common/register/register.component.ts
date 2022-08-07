import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  form: any = {
    username: '',
    password: '',
    firstName: '',
    lastName:'',
    email: '',
    phone: '',
    role: ''
  }
  roles: Array<string> = ["Customer", "Owner"]

  constructor(private authService: AuthService, private router: Router) { }

  ngOnInit(): void {
   
  }

  onClickSubmit() {
    const { username, password, firstName, lastName, email, phone, address, role } = this.form;
    const userRole = role == 'Owner' ? {
      "id": 1,
        "name": "owner"
    } : {
      "id": 2,
      "name": "customer"
    }
    const params = {
      "username": username,
      "password": password,
      "firstName": firstName,
      "lastName": lastName,
      "email": email,
      "phone": phone,
      "address": address,
      "role": userRole,
    }
    this.registerUser(params);
   
  }


  registerUser(params: any) {
    this.authService.register(params).subscribe(res => {
      if (res.data) {
        alert(res.message);
        this.router.navigate(['login'])
      } else {
        alert(res.message);
      }
    })
  }

}
