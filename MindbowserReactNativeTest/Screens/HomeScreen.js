/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow strict-local
 */

import axios from 'axios';
import Icon from 'react-native-vector-icons/FontAwesome';
import React from 'react';
import {
  FlatList,
  StyleSheet,
  Text,
  TouchableOpacity,
  View,
  Image,
  ActivityIndicator,
  ToastAndroid,
  Platform,
  AlertIOS,
  TextInput
} from 'react-native';
import database from '@react-native-firebase/database';
import { FAV_LIMIT_EXCEED, API, API_KEY, RANDOM_ID, LIMIT, OFFSET, RATING, SEARCH_PLACEHOLDER, NO_DESCRIPTION } from './Constants'

class HomeScreen extends React.Component {

  constructor(props) {
    super(props)
    this.state = {
      data: [],
      dataCopy: [],
      favData: [],
      isLoading: true,
      searchText: "",
    }
  }

  //to fetch data from sample API
  fetchUsers = async () => {
    this.setState({
      isLoading: true
    })

    axios.get(API, {
      params: {
        api_key: API_KEY,
        limit: LIMIT,
        offset: OFFSET,
        rating: RATING,
        random_id: RANDOM_ID
      }
    }).then((response) => {
      this.storedata(response.data)
    }).catch(function (error) {
      // handle error
      console.log(error);
      this.setState({
        isLoading: false
      })
    })
  };

  componentDidMount() {
    this.fetchUsers()
  }

  //to store data in state and make a copy to filter data
  storedata(response) {
    this.setState({
      data: response.data,
      dataCopy: response.data
    }, () => {
      this.getFavData()
    })
  }

  //to get fav data from firestore db
  getFavData() {
    database()
      .ref('/users')
      .on('value', snapshot => {
        let storedFavArray = [];
        snapshot.forEach((childSnapshot) => {
          childSnapshot.forEach((childSnap) => {
            if (childSnap.val() != null) {
              storedFavArray.push(childSnap.val())
            }
          });
        });
        let arrayIds = []
        this.state.data.forEach(element => {
          arrayIds.push(element.id)
        });


        this.setState({
          favData: storedFavArray.filter((item) => {
            return arrayIds.includes(item) && item
          }),
          isLoading: false,
        }, () => {
          // console.log("storedFavArray " + this.state.favData);
        })

      });
  }

  renderItem = (item) => (
    <TouchableOpacity onPress={() => this.props.navigation.navigate('Detail', {
      selectedItem: item,
    })}>
      <View style={styles.container}>
        <View style={{ flex: 0.20 }}>
          {
            item.user ?
              <Image
                source={{ uri: item.user && item.user.avatar_url }}
                style={styles.imageStyle}
              />
              :
              <Image
                source={require('../images/placeholder.jpg')}
                style={styles.imageStyle}
              />
          }
        </View>
        <View style={{ flex: 0.68, flexDirection: 'column', marginLeft: 10 }}>
          <Text style={{ color: 'black', fontSize: 16, fontWeight: 'bold' }}>{item.title}</Text>
          {
            (item.user && item.user.description) ?
              <Text numberOfLines={2} ellipsizeMode='tail' style={{ color: 'black', fontSize: 15, marginTop: 8 }}>{item.user && item.user.description}</Text>
              :
              <Text numberOfLines={2} ellipsizeMode='tail' style={{ color: 'black', fontSize: 15, marginTop: 8 }}>{NO_DESCRIPTION}</Text>
          }
        </View>
        <View style={{ flex: 0.12, alignItems: 'center' }}>
          <TouchableOpacity onPress={() => this.onFavClick(item)}>
            {this.checkFavMarked(item) ?
              <Icon name="heart" size={19} color="#ff0000"></Icon> : <Icon name="heart-o" size={19} color="#ff0000"></Icon>
            }
          </TouchableOpacity>
        </View>
      </View>
    </TouchableOpacity>
  );

  //check item marked as a favorite or not
  checkFavMarked = (item) => {
    return this.state.favData && this.state.favData.includes(item.id)
  }

  onFavClick = (item) => {
    if (this.checkFavMarked(item)) {

    } else {
      if (this.state.favData.length >= 5) {
        if (Platform.OS === 'android') {
          ToastAndroid.show(FAV_LIMIT_EXCEED, ToastAndroid.SHORT)
        } else {
          AlertIOS.alert(FAV_LIMIT_EXCEED);
        }
      } else {
        this.insertIntoFireStore(item)
      }
    }
  }

  //to insert fav record into fire store
  insertIntoFireStore(item) {
    const newReference = database().ref('/users').push();
    newReference
      .set({
        id: item.id,
      })
      .then(() => console.log('Data updated.'));
  }

  flatListItemSeparator = () => {
    return (
      <View
        style={{
          height: 1,
          width: "100%",
          backgroundColor: "#000",
        }}
      />
    );
  }

  _showIndicator = (animating) => {
    if (animating) {
      return (
        <View style={[styles.loading, { opacity: animating ? 1.0 : 0.0, backgroundColor: 'rgba(0,0,0,0.5)' }]}>
          <ActivityIndicator size='large' animating={true} color='white' />
        </View>
      );
    }
  }

  //to set search state
  searchData(text) {
    this.setState({
      searchText: text
    })
  }

  componentDidUpdate(prevProps, prevState) {
    if (prevState.searchText != this.state.searchText) {
      var filteredData = this.state.dataCopy.filter((item) => {
        return item.title.toLowerCase().includes(this.state.searchText.toLowerCase()) && item
      })
      this.setState({
        data: filteredData
      })
    }
  }

  render() {
    return (
      <View style={{ flex: 1 }}>
        {this._showIndicator(this.state.isLoading)}
        <TextInput
          placeholder={SEARCH_PLACEHOLDER}
          placeholderTextColor="#000"
          value={this.state.searchText}
          style={styles.input}
          onChangeText={(text) => { this.searchData(text) }}
        />
        <FlatList
          data={this.state.data}
          renderItem={({ item, index }) =>
            this.renderItem(item)
          }
          ItemSeparatorComponent={this.flatListItemSeparator}
          keyExtractor={item => item.id}
        />
      </View>
    );
  }

  componentWillUnmount() {
    // fix Warning: Can't perform a React state update on an unmounted component
    this.setState = (state, callback) => {
      return;
    };
  }
}

const styles = StyleSheet.create({
  container: {
    marginHorizontal: 10,
    marginVertical: 10,
    flexDirection: 'row',
    flex: 1
  },
  input: {
    height: 40,
    margin: 12,
    borderWidth: 1,
    color: 'black'
  },

  loading: {
    position: 'absolute',
    left: 0,
    right: 0,
    top: 0,
    bottom: 0,
    alignItems: 'center',
    justifyContent: 'center',
    elevation: 4
  },
  imageStyle: { width: 65, height: 65, borderRadius: 65, marginVertical: 5 }
});

export default HomeScreen;