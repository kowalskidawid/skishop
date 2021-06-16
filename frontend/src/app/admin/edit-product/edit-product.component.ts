import {Component, OnInit} from '@angular/core';
import {FormBuilder, Validators} from "@angular/forms";
import {HttpCategoriesService} from "../../services/HttpCategoriesService";
import {ActivatedRoute, Router} from "@angular/router";
import {HttpProductsService} from "../../services/HttpProductsService";

@Component({
    selector: 'app-edit-product',
    templateUrl: 'edit-product.component.html',
    styles: []
})
export class EditProductComponent implements OnInit {
    public productForm: any;
    public categories: any;
    public invalidForm = false;
    private productId;

    constructor(
        private formBuilder: FormBuilder,
        private categoriesService: HttpCategoriesService,
        private router: Router,
        private productsService: HttpProductsService,
        private activatedRoute: ActivatedRoute
    ) {
    }

    ngOnInit(): void {
        this.productId = Number(this.activatedRoute.snapshot.paramMap.get("productId"));
        this.productsService.getById(this.productId).subscribe(response => {
            this.productForm.get('price').setValue(response.price);
            this.productForm.get('name').setValue(response.name);
            this.productForm.get('description').setValue(response.description);
            this.productForm.get('category').setValue(response.category.id);
            this.productForm.get('inStock').setValue(response.inStock);
            console.log(response);
        });
        this.categoriesService.getAll().subscribe(response => {
            this.categories = response.categories;
        });
        this.productForm = this.formBuilder.group({
            price: [1.00, [Validators.min(0.01), Validators.required]],
            name: ['', Validators.required],
            description: ['', Validators.required],
            category: ['', Validators.required],
            inStock: ['', [Validators.required, Validators.min(1)]]
        });
    }

    editProduct() {
        this.invalidForm = false;
        if (!this.productForm.valid) {
            this.invalidForm = true;
            return;
        }
        let product = this.productForm.value;
        let categoryId = product.category;
        delete product.category;
        this.productsService.update(this.productId, categoryId, product).subscribe(response => {
            console.log(response);
            if (response.created) {
                this.router.navigate(['/admin/produkty']);
            }
        });
    }
}
