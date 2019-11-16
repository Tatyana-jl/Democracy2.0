import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IProblemType, defaultValue } from 'app/shared/model/problem-type.model';

export const ACTION_TYPES = {
  FETCH_PROBLEMTYPE_LIST: 'problemType/FETCH_PROBLEMTYPE_LIST',
  FETCH_PROBLEMTYPE: 'problemType/FETCH_PROBLEMTYPE',
  CREATE_PROBLEMTYPE: 'problemType/CREATE_PROBLEMTYPE',
  UPDATE_PROBLEMTYPE: 'problemType/UPDATE_PROBLEMTYPE',
  DELETE_PROBLEMTYPE: 'problemType/DELETE_PROBLEMTYPE',
  RESET: 'problemType/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IProblemType>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type ProblemTypeState = Readonly<typeof initialState>;

// Reducer

export default (state: ProblemTypeState = initialState, action): ProblemTypeState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_PROBLEMTYPE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PROBLEMTYPE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_PROBLEMTYPE):
    case REQUEST(ACTION_TYPES.UPDATE_PROBLEMTYPE):
    case REQUEST(ACTION_TYPES.DELETE_PROBLEMTYPE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_PROBLEMTYPE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PROBLEMTYPE):
    case FAILURE(ACTION_TYPES.CREATE_PROBLEMTYPE):
    case FAILURE(ACTION_TYPES.UPDATE_PROBLEMTYPE):
    case FAILURE(ACTION_TYPES.DELETE_PROBLEMTYPE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_PROBLEMTYPE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    case SUCCESS(ACTION_TYPES.FETCH_PROBLEMTYPE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_PROBLEMTYPE):
    case SUCCESS(ACTION_TYPES.UPDATE_PROBLEMTYPE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_PROBLEMTYPE):
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

const apiUrl = 'api/problem-types';

// Actions

export const getEntities: ICrudGetAllAction<IProblemType> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_PROBLEMTYPE_LIST,
    payload: axios.get<IProblemType>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IProblemType> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PROBLEMTYPE,
    payload: axios.get<IProblemType>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IProblemType> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PROBLEMTYPE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IProblemType> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PROBLEMTYPE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IProblemType> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PROBLEMTYPE,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
