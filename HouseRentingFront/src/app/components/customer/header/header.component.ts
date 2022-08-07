import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { CustomerService } from 'src/app/services/customer.service';
import { PropertyService } from 'src/app/services/property.service';
import { SessionService } from 'src/app/services/session.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css'],
})
export class HeaderComponent implements OnInit {
  isLoggedIn$: Observable<boolean> | undefined;
  isShow$: Observable<boolean> | undefined;
  isAdmin$: Observable<boolean> | undefined;
  isLoggedIn: boolean = false;
  isAdministrator: boolean = false;

  form: any = {
    keyword: '',
  };

  constructor(
    private sessionService: SessionService,
    private customerService: CustomerService,
    private propertyService: PropertyService,
    private router: Router
  ) {}
  cartItems: number = 0;
  user: any;

  ngOnInit(): void {
    this.initializeHeaderState();
    this.initializeUser();
  }

  initializeHeaderState(): void {
    this.isLoggedIn$ = this.customerService.isUserLoggedIn;
    this.isShow$ = this.customerService.isShow;
    this.isAdmin$ = this.customerService.isAdmin;
  }

  onSearchSubmit() {
    const { keyword } = this.form;
    if (keyword != '') {
      this.propertyService.searchWord.next(keyword);
      this.router.navigate(['/search-result']);
    }
  }

  initializeUser(): void {
    const textUser = this.sessionService.getUser();
    this.user = textUser ? JSON.parse(textUser) : null;
  }

  logout() {
    this.sessionService.logout();
    this.customerService.loggedIn.next(false);
    this.customerService.show.next(true);
    this.customerService.isAdmin.next(false);
    this.router.navigate(['/login']);
  }
}
