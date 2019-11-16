import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IOrganizationSize, defaultValue } from 'app/shared/model/organization-size.model';

export const ACTION_TYPES = {
  FETCH_ORGANIZATIONSIZE_LIST: 'organizationSize/FETCH_ORGANIZATIONSIZE_LIST',
  FETCH_ORGANIZATIONSIZE: 'organizationSize/FETCH_ORGANIZATIONSIZE',
  CREATE_ORGANIZATIONSIZE: 'organizationSize/CREATE_ORGANIZATIONSIZE',
  UPDATE_ORGANIZATIONSIZE: 'organizationSize/UPDATE_ORGANIZATIONSIZE',
  DELETE_ORGANIZATIONSIZE: 'organizationSize/DELETE_ORGANIZATIONSIZE',
  RESET: 'organizationSize/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IOrganizationSize>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type OrganizationSizeState = Readonly<typeof initialState>;

// Reducer

export default (state: OrganizationSizeState = initialState, action): OrganizationSizeState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ORGANIZATIONSIZE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ORGANIZATIONSIZE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_ORGANIZATIONSIZE):
    case REQUEST(ACTION_TYPES.UPDATE_ORGANIZATIONSIZE):
    case REQUEST(ACTION_TYPES.DELETE_ORGANIZATIONSIZE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_ORGANIZATIONSIZE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ORGANIZATIONSIZE):
    case FAILURE(ACTION_TYPES.CREATE_ORGANIZATIONSIZE):
    case FAILURE(ACTION_TYPES.UPDATE_ORGANIZATIONSIZE):
    case FAILURE(ACTION_TYPES.DELETE_ORGANIZATIONSIZE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_ORGANIZATIONSIZE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    case SUCCESS(ACTION_TYPES.FETCH_ORGANIZATIONSIZE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_ORGANIZATIONSIZE):
    case SUCCESS(ACTION_TYPES.UPDATE_ORGANIZATIONSIZE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_ORGANIZATIONSIZE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/organization-sizes';

// Actions

export const getEntities: ICrudGetAllAction<IOrganizationSize> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_ORGANIZATIONSIZE_LIST,
    payload: axios.get<IOrganizationSize>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IOrganizationSize> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ORGANIZATIONSIZE,
    payload: axios.get<IOrganizationSize>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IOrganizationSize> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ORGANIZATIONSIZE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IOrganizationSize> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ORGANIZATIONSIZE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IOrganizationSize> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ORGANIZATIONSIZE,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
