import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { PropertyListComponent } from './components/customer/property-list/property-list.component';
import { PropertyDetailComponent } from './components/customer/property-detail/property-detail.component';
import { ManagePropertyComponent } from './components/owner/manage-property/manage-property.component';
import { SearchResultComponent } from './components/customer/search-result/search-result.component';
import { LoginComponent } from './components/common/login/login.component';
import { RegisterComponent } from './components/common/register/register.component';
import { HeaderComponent } from './components/customer/header/header.component';
import { ChatUiComponent } from './components/customer/chat-ui/chat-ui.component';
import { HttpClientModule,  HTTP_INTERCEPTORS } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { SidebarComponent } from './components/owner/sidebar/sidebar.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatToolbarModule } from '@angular/material/toolbar';
import {MatIconModule} from '@angular/material/icon';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatListModule} from '@angular/material/list';
import { PropertyItemComponent } from './components/common/property-item/property-item.component';
import { FilterComponent } from './components/common/filter/filter.component';
import { UpdatePropertyComponent } from './components/common/update-property/update-property.component';
import { ComfortReservationComponent } from './components/owner/comfort-reservation/comfort-reservation.component';
import { OwnerChatUiComponent } from './components/owner/owner-chat-ui/owner-chat-ui.component';
import { JwtHelperService, JWT_OPTIONS } from '@auth0/angular-jwt';
import { JwtAuthInterceptor } from './jwt-auth.interceptor';
import { ChatDatePipe } from './chat-date.pipe';
import { ReservationSaleComponent } from './components/owner/reservation-sale/reservation-sale.component';
import { ReservationHistoryComponent } from './components/customer/reservation-history/reservation-history.component';
import { ChatComponent } from './components/common/chat/chat.component';
import { PropertyGalleryComponent } from './components/owner/property-gallery/property-gallery.component';
import { NgxPaginationModule } from 'ngx-pagination';
@NgModule({
  declarations: [
    AppComponent,
    PropertyListComponent,
    PropertyDetailComponent,
    ManagePropertyComponent,
    SearchResultComponent,
    LoginComponent,
    RegisterComponent,
    HeaderComponent,
    SidebarComponent,
    ChatUiComponent,
    PropertyItemComponent,
    FilterComponent,
    UpdatePropertyComponent,
    ComfortReservationComponent,
    OwnerChatUiComponent,
    ChatDatePipe,
    ReservationSaleComponent,
    ReservationHistoryComponent,
    ChatComponent,
    PropertyGalleryComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    CommonModule,
    RouterModule,
    FormsModule,
    MatToolbarModule,
    MatIconModule,
    MatSidenavModule,
    MatListModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    NgxPaginationModule
  ],
  providers: [{ provide: JWT_OPTIONS, useValue: JWT_OPTIONS },
    JwtHelperService, {
      provide:  HTTP_INTERCEPTORS,
      useClass: JwtAuthInterceptor,
      multi: true
    }],
  bootstrap: [AppComponent]
})
export class AppModule { }
