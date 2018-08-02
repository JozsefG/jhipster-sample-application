import { ITeam } from 'app/shared/model//team.model';

export interface IOrganization {
    id?: number;
    name?: string;
    teams?: ITeam[];
}

export class Organization implements IOrganization {
    constructor(public id?: number, public name?: string, public teams?: ITeam[]) {}
}
