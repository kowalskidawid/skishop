import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CategoriesComponent } from './categories/categories.component';
import { AddCategoryComponent } from './add-category/add-category.component';
import {AppRoutingModule} from "../app-routing.module";
import {ReactiveFormsModule} from "@angular/forms";
import { AddProductComponent } from './add-product/add-product.component';
import { EditCategoryComponent } from './edit-category/edit-category.component';
import { ProductsComponent } from './products/products.component';
import { OrdersComponent } from './orders/orders.component';
import { EditProductComponent } from './edit-product/edit-product.component';
import { UsersComponent } from './users/users.component';


@NgModule({
  declarations: [
    CategoriesComponent,
    AddCategoryComponent,
    AddProductComponent,
    EditCategoryComponent,
    ProductsComponent,
    OrdersComponent,
    EditProductComponent,
    UsersComponent
  ],
    imports: [
        CommonModule,
        AppRoutingModule,
        ReactiveFormsModule,
    ]
})
export class AdminModule { }
