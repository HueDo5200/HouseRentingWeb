import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/common/login/login.component';
import { RegisterComponent } from './components/common/register/register.component';
import { ChatUiComponent } from './components/customer/chat-ui/chat-ui.component';
import { PropertyDetailComponent } from './components/customer/property-detail/property-detail.component';
import { PropertyListComponent } from './components/customer/property-list/property-list.component';
import { SearchResultComponent } from './components/customer/search-result/search-result.component';
import { ComfortReservationComponent } from './components/owner/comfort-reservation/comfort-reservation.component';
import { ManagePropertyComponent } from './components/owner/manage-property/manage-property.component';
import { OwnerChatUiComponent } from './components/owner/owner-chat-ui/owner-chat-ui.component';
import { RouteGuardService } from './services/route-guard.service';
import { ReservationSaleComponent } from './components/owner/reservation-sale/reservation-sale.component';
import { ReservationHistoryComponent } from './components/customer/reservation-history/reservation-history.component';
import { ChatComponent } from './components/common/chat/chat.component';
import { PropertyGalleryComponent } from './components/owner/property-gallery/property-gallery.component';
const routes: Routes = [
  {path: '', redirectTo : 'property-list', pathMatch: 'full'},
  {path: 'property-list', component: PropertyListComponent},
  {path: 'search-result', component: SearchResultComponent},
  {path: 'manage-property', component: ManagePropertyComponent, canActivate : [RouteGuardService], data: {expectedRole : 'owner'}},
  {path: 'login', component: LoginComponent},
  {path: 'manage-reservation', component: ComfortReservationComponent, canActivate : [RouteGuardService], data: {expectedRole : 'owner'}},
  {path: 'property-detail/:id', component: PropertyDetailComponent},
  {path: 'customer/chat', component: ChatUiComponent, canActivate : [RouteGuardService], data: {expectedRole : 'customer'}},
  {path: 'owner/chat', component: OwnerChatUiComponent, canActivate : [RouteGuardService], data: {expectedRole : 'owner'}},
  {path: 'register', component: RegisterComponent},
  {path: 'reservation/sale', component: ReservationSaleComponent, canActivate : [RouteGuardService], data: {expectedRole : 'owner'}},
  {path: 'customer/reservation/history', component: ReservationHistoryComponent, canActivate : [RouteGuardService], data: {expectedRole : 'customer'}},
   {path: 'property/:id/gallery', component: PropertyGalleryComponent, canActivate: [RouteGuardService], data: {expectedRole : 'owner'}}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
