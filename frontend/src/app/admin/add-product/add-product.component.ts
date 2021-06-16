import {Component, OnInit} from '@angular/core';
import {FormBuilder, Validators} from "@angular/forms";
import {HttpCategoriesService} from "../../services/HttpCategoriesService";
import {Router} from "@angular/router";
import {HttpProductsService} from "../../services/HttpProductsService";

@Component({
    selector: 'app-add-product',
    templateUrl: './add-product.component.html',
    styles: []
})
export class AddProductComponent implements OnInit {
    public productForm: any;
    public categories: any;
    public invalidForm = false;

    constructor(
        private formBuilder: FormBuilder,
        private categoriesService: HttpCategoriesService,
        private router: Router,
        private productsService: HttpProductsService
    ) {
    }

    ngOnInit(): void {
        this.categoriesService.getAll().subscribe(response => {
            this.categories = response.categories;
        });
        this.productForm = this.formBuilder.group({
            price: [1.00, [Validators.min(0.01), Validators.required]],
            name: ['', Validators.required],
            description: ['', Validators.required],
            category: ['', Validators.required],
            inStock: ['', [Validators.required, Validators.min(1)]],
            properties: this.formBuilder.array([this.createProductPropertyFiled()])
        });
    }

    createProductPropertyFiled() {
        return this.formBuilder.group({
            propertyName: ['', Validators.required],
            propertyValue: ['', Validators.required]
        });
    }

    addProductProperty() {
        this.productForm.get('properties').push(this.createProductPropertyFiled());
    }

    addProduct() {
        this.invalidForm = false;
        console.log(this.productForm.value);
        if (!this.productForm.valid) {
            this.invalidForm = true;
            return;
        }
        let product = this.productForm.value;
        let category = product.category;
        delete product.category;
        this.productsService.create(category, product).subscribe(response => {
            if (response.created) {
                this.router.navigate(['/admin/produkty']);
            }
        });
    }
}
