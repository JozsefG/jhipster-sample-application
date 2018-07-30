export interface IXUser {
    id?: number;
    name?: string;
}

export class XUser implements IXUser {
    constructor(public id?: number, public name?: string) {}
}
