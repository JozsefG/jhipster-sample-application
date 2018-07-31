import { IOrganization } from 'app/shared/model//organization.model';

export interface IXTeam {
    id?: number;
    name?: string;
    organization?: IOrganization;
}

export class XTeam implements IXTeam {
    constructor(public id?: number, public name?: string, public organization?: IOrganization) {}
}
