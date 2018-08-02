import { IXUser } from 'app/shared/model//x-user.model';
import { ITeam } from 'app/shared/model//team.model';

export interface IXGroup {
    id?: number;
    name?: string;
    users?: IXUser[];
    team?: ITeam;
}

export class XGroup implements IXGroup {
    constructor(public id?: number, public name?: string, public users?: IXUser[], public team?: ITeam) {}
}
