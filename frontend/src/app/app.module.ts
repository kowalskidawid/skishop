import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HomeComponent} from './home/home.component';
import {ContactComponent} from './contact/contact.component';
import {CheckoutComponent} from './checkout/checkout.component';
import {NavbarComponent} from './partial/navbar/navbar.component';
import {CategoriesComponent} from './categories/categories.component';
import {FooterComponent} from './partial/footer/footer.component';
import {RegisterComponent} from './register/register.component';
import {HttpProductsService} from "./services/HttpProductsService";
import {HttpClient, HttpClientModule} from "@angular/common/http";
import {CommonModule} from "@angular/common";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HttpCategoriesService} from "./services/HttpCategoriesService";
import {CategoryComponent} from './category/category.component';
import {CartService} from "./services/CartService";
import {HttpUsersService} from "./services/HttpUsersService";
import {AdminModule} from "./admin/admin.module";
import { LoginComponent } from './login/login.component';
import { ProductComponent } from './product/product.component';
import { ErrorComponent } from './error/error.component';
import { OrderConfirmedComponent } from './order-confirmed/order-confirmed.component';
import { LogoutComponent } from './logout/logout.component';
import { SearchComponent } from './search/search.component';

@NgModule({
    declarations: [
        AppComponent,
        ContactComponent,
        CheckoutComponent,
        NavbarComponent,
        CategoriesComponent,
        FooterComponent,
        RegisterComponent,
        CategoryComponent,
        LoginComponent,
        ProductComponent,
        ErrorComponent,
        HomeComponent,
        OrderConfirmedComponent,
        LogoutComponent,
        SearchComponent
    ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        CommonModule,
        HttpClientModule,
        FormsModule, ReactiveFormsModule,
        AdminModule
    ],
    providers: [
        HttpProductsService,
        HttpCategoriesService,
        CartService,
        HttpUsersService
    ],
    bootstrap: [AppComponent],
})
export class AppModule {
}
