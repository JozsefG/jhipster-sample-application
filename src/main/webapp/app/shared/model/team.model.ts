import { IXGroup } from 'app/shared/model//x-group.model';
import { IXUser } from 'app/shared/model//x-user.model';
import { IOrganization } from 'app/shared/model//organization.model';

export interface ITeam {
    id?: number;
    name?: string;
    groups?: IXGroup[];
    users?: IXUser[];
    organization?: IOrganization;
}

export class Team implements ITeam {
    constructor(
        public id?: number,
        public name?: string,
        public groups?: IXGroup[],
        public users?: IXUser[],
        public organization?: IOrganization
    ) {}
}
