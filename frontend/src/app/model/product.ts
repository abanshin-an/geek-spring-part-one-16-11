export class Product {

  constructor(public id: number | null,
              public name: string,
              public description: string,
              public categoryId: number,
              public categoryName: string,
              public price: number
  ) {
  }
}
