import { Component, OnInit } from '@angular/core';
import {ProductService} from "../services/product.service";
import {Product} from "../model/product";
import {Router} from "@angular/router";
import {Page} from "../model/page";

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.less']
})
export class ProductListComponent implements OnInit {

  products:Product[]=[];

  constructor(private productService:ProductService,
              private router:Router) { }

  ngOnInit(): void {
    this.productService.findAll(1)
      .subscribe(res=>{
        this.products=res.content;
      },
        err=>{
        console.error(err);
        })
  }
  public delete(id:number | null,
                i:number) {
    console.info("execute delete()"+id)
    if (id==null)
      return;
    this.productService.delete(id)
      .subscribe(res => {
        console.info("remove index "+i);
          //this.products.splice(i,1); // Можно просто удалить этот элемент из списка на клиенте
        this.productService.findAll(1)
          .subscribe(res=>{
              this.products=res.content;
            },
            err=>{
              console.error(err);
            })}
        ,
        err => {
          console.error(err);
        });

  }
}
