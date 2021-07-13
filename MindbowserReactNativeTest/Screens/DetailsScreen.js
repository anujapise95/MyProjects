/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow strict-local
 */

import React from 'react';
import {
  ScrollView,
  StyleSheet,
  Text,
  Image,
  View,
} from 'react-native';


class DetailsScreen extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      selectedItemDetail: {}
    }
  }

  componentDidMount() {
    this.setState({
      selectedItemDetail: this.props.route.params.selectedItem
    })
  }

  render() {
    return (
      <ScrollView style={{flex:1}}>
        <View style={styles.container}>
          <Text style={styles.sectionTitle}>{this.state.selectedItemDetail.title}</Text>
          {
            this.state.selectedItemDetail.user ?
              <Image
                source={{ uri: this.state.selectedItemDetail.user.avatar_url }}
                style={styles.imageStyle}
              />
              :
              <Image
                source={require('../images/placeholder.jpg')}
                style={styles.imageStyle}
              />
          }
          <Text style={styles.textStyle}>{this.state.selectedItemDetail.user && this.state.selectedItemDetail.user.description}</Text>
        </View>
      </ScrollView>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    flexDirection: 'column',
    marginVertical: 10,
    marginHorizontal: 10,
    alignItems: 'center'
  },
  sectionTitle: {
    textAlign: 'center',
    fontSize: 20,
    color: 'black',
    fontWeight: 'bold',
  },
  textStyle: {
    color: 'black',
    fontSize: 15,
    marginTop: 8
  },
  imageStyle: { 
    marginTop: 15, 
    width: 150, 
    height: 150,
    marginVertical: 5 
  }
});

export default DetailsScreen;
