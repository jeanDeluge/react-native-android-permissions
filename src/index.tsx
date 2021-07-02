import { NativeModules } from 'react-native';

type AndroidPermissionsType = {
  multiply(a: number, b: number): Promise<number>;
};

const { AndroidPermissions } = NativeModules;

export default AndroidPermissions as AndroidPermissionsType;
