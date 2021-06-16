import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {CategoriesComponent} from './categories/categories.component';
import {HomeComponent} from './home/home.component';
import {RegisterComponent} from './register/register.component';
import {CategoryComponent} from "./category/category.component";
import {CategoriesComponent as AdminCategoriesComponent} from './admin/categories/categories.component';
import {AddCategoryComponent} from "./admin/add-category/add-category.component";
import {LoginComponent} from "./login/login.component";
import {AddProductComponent} from "./admin/add-product/add-product.component";
import {ProductComponent} from "./product/product.component";
import {CheckoutComponent} from "./checkout/checkout.component";
import {EditCategoryComponent} from "./admin/edit-category/edit-category.component";
import {ErrorComponent} from "./error/error.component";
import {ProductsComponent as AdminProductsComponent} from "./admin/products/products.component";
import {OrdersComponent} from "./admin/orders/orders.component";
import {EditProductComponent} from "./admin/edit-product/edit-product.component";
import {LogoutComponent} from "./logout/logout.component";
import {UsersComponent} from "./admin/users/users.component";
import {SearchComponent} from "./search/search.component";

const routes: Routes = [
    {
        path: '',
        component: HomeComponent
    },
    {
        path: 'kategorie',
        component: CategoriesComponent
    },
    {
        path: 'rejestracja',
        component: RegisterComponent
    },
    {
        path: 'logowanie',
        component: LoginComponent,
    },
    {
        path: 'produkty/:categoryId',
        component: CategoryComponent
    },
    {
        path: 'produkt/:productId',
        component: ProductComponent
    },
    {
        path: 'admin/kategorie',
        children: [
            {
                path: '',
                component: AdminCategoriesComponent,
            },
            {
                path: 'dodaj',
                component: AddCategoryComponent
            },
            {
                path: ':id/edytuj',
                component: EditCategoryComponent
            },
        ]
    },
    {
        path: 'admin/produkty',
        children: [
            {
                path: '',
                component: AdminProductsComponent
            },
            {
                path: ':categoryId/:page',
                component: AdminProductsComponent
            },
            {
                path: 'dodaj',
                component: AddProductComponent
            },
        ],
    },
    {
        path: 'admin/produkt/:productId/edytuj',
        component: EditProductComponent
    },
    {
        path: 'admin/zamowienia',
        children: [
            {
                path: '',
                component: OrdersComponent,
            },
        ]
    },
    {
        path: 'admin/uzytkownicy',
        component: UsersComponent
    },
    {
        path: 'zloz-zamowienie',
        component: CheckoutComponent
    },
    {
        path: 'wyloguj',
        component: LogoutComponent
    },
    {
        path: 'szukaj/:query',
        component: SearchComponent
    },
    {
        path: "**",
        component: ErrorComponent
    }
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
