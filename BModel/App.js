/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow
 */

import React, { Component } from 'react';
import { Platform, StyleSheet, Text, View, Image } from 'react-native';

const instructions = Platform.select({
  ios: 'Press Cmd+R to reload,\n' + 'Cmd+D or shake for dev menu',
  android:
    'Double tap R on your keyboard to reload,\n' +
    'Shake or press menu button for dev menu',
});

type Props = {};
export default class App extends Component<Props> {

  constructor(props) {
    super(props);
    this.state = {
      data: null
    };
  }

  componentDidMount() {
    return fetch('http://192.168.100.14:8080/bundle/2')
      .then((response) => response.json())
      .then((responseJson) => {

        console.warn(responseJson)

        this.setState({
          data: responseJson,
        })

      })
      .catch((error) => {
        console.warn(error);
      });
  }
  render() {
    return (
      <View style={styles.container}>
        <Text style={styles.welcome}>React Native</Text>
        <Text style={styles.welcome}>{this.state.data == null ? "" : this.state.data.name}</Text>
        <Text style={styles.welcome}></Text>
        <Image source={require('./img/light_open.png')} />
        <Image
          source={
            { uri: this.state.data == null ? "" : this.state.data.logo, width: 240, height: 64 }
          }
        />
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
});
