import { IXUser } from 'app/shared/model//x-user.model';

export interface IRole {
    id?: number;
    name?: string;
    users?: IXUser[];
}

export class Role implements IRole {
    constructor(public id?: number, public name?: string, public users?: IXUser[]) {}
}
