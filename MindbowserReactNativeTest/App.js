/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow strict-local
 */

import axios from 'axios';
import React from 'react';
import HomeScreen from './Screens/HomeScreen';
import { NavigationContainer } from '@react-navigation/native';
import { createStackNavigator } from '@react-navigation/stack';
import DetailsScreen from './Screens/DetailsScreen';


// const RootStack = createStackNavigator(
//   {
//     Home: HomeScreen,
//     Details: DetailsScreen,
//   },
//   {
//     initialRouteName: 'Home',
//   }
// );
// const { Navigator, Screen } = createStackNavigator();
const Stack = createStackNavigator();
const AppStack = () => (
  <Stack.Navigator
    initialRouteName="Home"
    headerMode="screen"
    screenOptions={{
      headerTintColor: 'white',
      headerStyle: { backgroundColor: '#0153A5' },
    }}
  >
    <Stack.Screen
      name="Home"
      component={HomeScreen}
      options={{
        title: 'Home',
      }}
    />
    <Stack.Screen
      name="Detail"
      component={DetailsScreen}
      options={{
        title: 'Detail',
      }}
    />
  </Stack.Navigator>
);

export default class App extends React.Component {
  render() {
    return <NavigationContainer>
      <AppStack />
    </NavigationContainer>;
  }
}
