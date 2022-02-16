export class City {

  id: number | undefined;
  name: string | undefined;
  imageUrl: string | undefined;

  deserialize(input: any): this {
    Object.assign(this, input);
    return this;
  }

}
