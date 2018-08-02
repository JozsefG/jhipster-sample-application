import { ITeam } from 'app/shared/model//team.model';
import { IXGroup } from 'app/shared/model//x-group.model';
import { IRole } from 'app/shared/model//role.model';

export interface IXUser {
    id?: number;
    name?: string;
    team?: ITeam;
    groups?: IXGroup[];
    roles?: IRole[];
}

export class XUser implements IXUser {
    constructor(public id?: number, public name?: string, public team?: ITeam, public groups?: IXGroup[], public roles?: IRole[]) {}
}
