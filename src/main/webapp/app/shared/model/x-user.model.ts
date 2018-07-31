import { IXGroup } from 'app/shared/model//x-group.model';

export interface IXUser {
    id?: number;
    name?: string;
    xgroups?: IXGroup[];
}

export class XUser implements IXUser {
    constructor(public id?: number, public name?: string, public xgroups?: IXGroup[]) {}
}
