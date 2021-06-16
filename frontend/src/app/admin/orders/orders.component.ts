import {Component, OnInit} from '@angular/core';
import {HttpOrdersService} from "../../services/HttpOrdersService";
import {HttpUsersService} from "../../services/HttpUsersService";
import {Router} from "@angular/router";

@Component({
    selector: 'app-orders',
    templateUrl: 'orders.component.html',
    styles: []
})
export class OrdersComponent implements OnInit {
    public response = {
        count: 0,
        orders: []
    };

    constructor(private ordersService: HttpOrdersService, private usersService: HttpUsersService, private router: Router) {
    }

    ngOnInit(): void {
        this.refreshList();
    }

    refreshList() {
        this.ordersService.getAll().subscribe(response => {
            this.response = response;
        })
    }

    getStatusText(status: string) {
        let text = 'Złożone zamówienie';
        if (status == 'PAID') {
            text = 'Zamównienie opłacone';
        } else if (status == 'SENT') {
            text = 'Wysłano zamówienie';
        } else if (status == 'DELIVERED') {
            text = 'Dostarczono zamówienie';
        }

        return text;
    }

    getNextStatus(status: string) {
        let text = '';
        if (status == 'PLACED_ORDER') {
            text = 'Zamównienie opłacone';
        } else if (status == 'PAID') {
            text = 'Wysłano zamówienie'
        } else if (status == 'SENT') {
            text = 'Dostarczono zamówienie';
        }

        return text;
    }

    changeStatus(order: any) {
        let currentStatus = order.status;
        let status = '';
        if (currentStatus == 'PLACED_ORDER') {
            status = 'PAID';
        } else if (currentStatus == 'PAID') {
            status = 'SENT';
        } else if (currentStatus == 'SENT') {
            status = 'DELIVERED';
        } else {
            return;
        }

        this.ordersService.updateStatus(order.id, status).subscribe(response => {
            if (response.updated) {
                this.refreshList();
            } else {
                this.router.navigate(['cos-nie-tak']);
            }
        });
    }
}
