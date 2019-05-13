import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IMestre, defaultValue } from 'app/shared/model/mestre.model';

export const ACTION_TYPES = {
  FETCH_MESTRE_LIST: 'mestre/FETCH_MESTRE_LIST',
  FETCH_MESTRE: 'mestre/FETCH_MESTRE',
  CREATE_MESTRE: 'mestre/CREATE_MESTRE',
  UPDATE_MESTRE: 'mestre/UPDATE_MESTRE',
  DELETE_MESTRE: 'mestre/DELETE_MESTRE',
  RESET: 'mestre/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IMestre>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type MestreState = Readonly<typeof initialState>;

// Reducer

export default (state: MestreState = initialState, action): MestreState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_MESTRE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_MESTRE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_MESTRE):
    case REQUEST(ACTION_TYPES.UPDATE_MESTRE):
    case REQUEST(ACTION_TYPES.DELETE_MESTRE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_MESTRE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_MESTRE):
    case FAILURE(ACTION_TYPES.CREATE_MESTRE):
    case FAILURE(ACTION_TYPES.UPDATE_MESTRE):
    case FAILURE(ACTION_TYPES.DELETE_MESTRE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_MESTRE_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_MESTRE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_MESTRE):
    case SUCCESS(ACTION_TYPES.UPDATE_MESTRE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_MESTRE):
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

const apiUrl = 'api/mestres';

// Actions

export const getEntities: ICrudGetAllAction<IMestre> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_MESTRE_LIST,
    payload: axios.get<IMestre>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IMestre> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_MESTRE,
    payload: axios.get<IMestre>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IMestre> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_MESTRE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IMestre> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_MESTRE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IMestre> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_MESTRE,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
