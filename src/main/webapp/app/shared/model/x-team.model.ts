import { IOrganization } from 'app/shared/model//organization.model';
import { IXGroup } from 'app/shared/model//x-group.model';

export interface IXTeam {
    id?: number;
    name?: string;
    organization?: IOrganization;
    xgroups?: IXGroup[];
}

export class XTeam implements IXTeam {
    constructor(public id?: number, public name?: string, public organization?: IOrganization, public xgroups?: IXGroup[]) {}
}
